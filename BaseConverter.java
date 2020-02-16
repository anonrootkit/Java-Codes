import java.io.*;

class BaseConverter{
    
    private BufferedReader reader;
    private String inputNumber;
    private int inputNumberBase;
    private int outputNumberBase;
    private String outputNumber;
    private final int MIN_BASE = 1;
    private final int BASE_10 = 10;
    private final char VALUES[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 
                                'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 
                                'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private final int MAX_BASE = VALUES.length;
    
    public static void main(String...args) throws Exception{
        ATAC program = new ATAC();
        program.initVars();
        program.takeInput();
        
        switch(program.validate()){
            case -1:
                program.error();
                break;
        
            case 0:
                program.calculate();
                program.output();
                break;
        }
        
    }
    
    public void output(){
        System.out.print(inputNumber + " : " + outputNumber);
    }
    
    public void initVars() throws IOException{
        reader = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public void takeInput() throws IOException{
        System.out.print("Enter the number: ");
        inputNumber = reader.readLine();
        
        System.out.print("Enter the base of the entered number (max 16) :");
        inputNumberBase = Integer.parseInt(reader.readLine());
        
        System.out.print("Enter the base to convert the number to (max 16) : ");
        outputNumberBase = Integer.parseInt(reader.readLine());
    }
    
    public void calculate(){
        if(inputNumberBase == outputNumberBase){
            outputNumber = inputNumber;
            return;
        }
        
        convertToDecimal();
        convertToOutputBase();
    }
    
    public void convertToOutputBase(){
        if(outputNumberBase == BASE_10) return;
        
        int temp = Integer.parseInt(outputNumber);
        
        outputNumber = "";
        if(outputNumberBase == MIN_BASE){
            for (;temp>0;temp--){
                outputNumber+="1";
            }
        }else{
            while (temp > 0){
                int mod = temp % outputNumberBase;
                temp /= outputNumberBase;
                outputNumber = VALUES[mod] + outputNumber;
            }
        }
    }
    
    public void convertToDecimal(){
        int sum = getIndex(inputNumber.charAt(0));
        
        int length = inputNumber.length();
        
        if(inputNumberBase == MIN_BASE){
            for(int i = 1; i < length; i++){
                sum += 1;
            }
        }else{
            for (int i = 1; i < length; i++){
                sum = sum*inputNumberBase + getIndex(inputNumber.charAt(i));
            }
        }
        
        outputNumber = String.valueOf(sum);
    }
    
    public int validate(){
        if(inputNumberBase > MAX_BASE || inputNumberBase < MIN_BASE)
            return -1;
        
        int length =  inputNumber.length();
        for (int i = 0; i < length; i++){
            int index = getIndex(inputNumber.charAt(i));
            if(index != 1 && inputNumberBase == MIN_BASE){
                return -1;
            } else if((index >= inputNumberBase || index == -1) && inputNumberBase != MIN_BASE){
                return -1;
            }
        }
        return 0;
    }
    
    public void error(){
        System.out.print("Invalid base or number entered.");
    }
    
    public int getIndex(char c){
        int length = VALUES.length;
        for (int i = 0; i < length; i++){
            if(c != VALUES[i]) continue;
            return i;
        }
        return -1;
    }
}