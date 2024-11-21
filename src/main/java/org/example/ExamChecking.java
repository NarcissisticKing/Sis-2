public class ExamChecking extends Thread {
    private static final int TOTAL = 500; // Total exam sheets to check
    private static final int SHEETS_PER_ASSISTANT = 50; // Sheets each assistant can check
    private static int remainingSheets = TOTAL; // Shared counter
    private int iterations; // Max iterations this assistant will work
    private int assistantId; // Unique ID for the assistant

    // Constructor
    public ExamChecking(int iterations, int assistantId) {
        this.iterations = iterations;
        this.assistantId = assistantId;
    }

    @Override
    public void run() {
        int sheetsChecked = 0; // Count sheets checked by this thread
        for (int i = 0; i < iterations; i++) {
            synchronized (ExamChecking.class) { // Lock on the class to ensure thread safety
                if (remainingSheets >= SHEETS_PER_ASSISTANT) {
                    remainingSheets -= SHEETS_PER_ASSISTANT;
                    sheetsChecked += SHEETS_PER_ASSISTANT;
                    System.out.println("Assistant " + assistantId + " checked 50 sheets. Remaining: " + remainingSheets);
                } else if (remainingSheets > 0) {
                    int remainingForThisIteration = remainingSheets;
                    remainingSheets = 0;
                    sheetsChecked += remainingForThisIteration;
                    System.out.println("Assistant " + assistantId + " checked " + remainingForThisIteration + " sheets. Remaining: 0");
                    break; // No more sheets left
                } else {
                    System.out.println("Assistant " + assistantId + " found no sheets to check.");
                    break; // Exit if no sheets remain
                }
            }
            try {
                Thread.sleep(100); //time taken for check
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Assistant " + assistantId + " finished checking " + sheetsChecked + " sheets.");
    }

    public static void main(String[] args) {
        int numberOfAssistants = 10; // Total thr
        int iterationsPerAssistant = 6; // Iterations each assistant will perform

        // Create and start thr
        for (int i = 1; i <= numberOfAssistants; i++) {
            ExamChecking assistant = new ExamChecking(iterationsPerAssistant, i);
            assistant.start();
        }
    }
}
