public class Main {
    public static void main(String[] args) {
        Computer computer = new Computer("Appke", "N67", new Processor(5, 6, "apl", 5), new OperationallyMemory("OK", 5, 5), new StorageInformation("SSD", 5, 5), new Screen(5, "tYPE", 5), new Keyboard("hb", true, 5));
        System.out.println(computer);
        computer.returnWeight();

        computer.setProcessor(new Processor(10, 10, "DJD", 10));
        System.out.println(computer);
        computer.returnWeight();




    }
}
