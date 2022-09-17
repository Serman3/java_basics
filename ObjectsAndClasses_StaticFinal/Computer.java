public class Computer {

    private final String vendor;
    private final String name;
    private Processor processor;
    private OperationallyMemory operationallyMemory;
    private StorageInformation storageInformation;
    private Screen screen;
    private Keyboard keyboard;

    public Computer(String vendor, String name, Processor processor, OperationallyMemory operationallyMemory, StorageInformation storageInformation, Screen screen, Keyboard keyboard) {
        this.vendor = vendor;
        this.name = name;
        this.processor = processor;
        this.operationallyMemory = operationallyMemory;
        this.storageInformation = storageInformation;
        this.screen = screen;
        this.keyboard = keyboard;
    }

    public Computer setProcessor(Processor processor){
        return  new Computer(vendor, name, processor, operationallyMemory, storageInformation, screen, keyboard );
    }

    public Computer setOperationallyMemory(OperationallyMemory operationallyMemory){
        return  new Computer(vendor, name, processor, operationallyMemory, storageInformation, screen, keyboard );
    }

    public Computer setStorageInformation(StorageInformation storageInformation){
        return  new Computer(vendor, name, processor, operationallyMemory, storageInformation, screen, keyboard );
    }

    public Computer setScreen(Screen screen){
        return  new Computer(vendor, name, processor, operationallyMemory, storageInformation, screen, keyboard );
    }

    public Computer setKeyboard(Keyboard keyboard){
        return  new Computer(vendor, name, processor, operationallyMemory, storageInformation, screen, keyboard );
    }

    public int returnWeight(){
        int sum = processor.getWeight() + operationallyMemory.getWeight() + storageInformation.getWeight() + screen.getWeight() + keyboard.getWeight();
        System.out.println("weight computer " + sum);
        return sum;
    }

    public String getVendor() {
        return vendor;
    }

    public String getName() {
        return name;
    }

    public Processor getProcessor() {
        return processor;
    }

    public OperationallyMemory getOperationallyMemory() {
        return operationallyMemory;
    }

    public StorageInformation getStorageInformation() {
        return storageInformation;
    }

    public Screen getScreen() {
        return screen;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "vendor='" + vendor + '\'' +
                ", name='" + name + '\'' +
                ", processor=" + processor +
                ", operationallyMemory=" + operationallyMemory +
                ", storageInformation=" + storageInformation +
                ", screen=" + screen +
                ", keyboard=" + keyboard +
                '}';
    }
}
