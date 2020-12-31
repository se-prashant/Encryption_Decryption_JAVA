
import java.util.Scanner;
import java.io.*;
class Main {
    interface ShiftAlgo{
    }
    interface UnicodeAlgo{
    }
    interface Algorithm extends ShiftAlgo,UnicodeAlgo{
    }
    static class Decode implements Algorithm  {
         Decode(int key,String data,File pathToFile,File destination,String algo){
             if ("unicode".equals(algo)) {
                 new ImplementUnicodeAlgo(key, data, pathToFile, destination);
             } else {
                 new ImplementShiftAlgo(key, data, pathToFile, destination);
             }
        }
    }
    static class ImplementShiftAlgo{
        int key;
        String data;
        File pathToFile;
        File destination;

        ImplementShiftAlgo(int key,String data,File pathToFile,File destination){
            this.key = key;
            this.data = data;
            this.pathToFile = pathToFile;
            this.destination = destination;
            String decoded = decode();
            if(destination!=null) {
                try {
                    FileWriter writer = new FileWriter(destination);
                    for(int i =0;i<decoded.length();i++)
                    {
                        writer.write(decoded.charAt(i));
                    }
                    writer.close();
                }catch ( IOException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println(decoded);
            }
        }

        public String decode(){
            String out = "";
            if(!data.equals("")){
                out = encoded(data);

            }else{
                try (Scanner scanner = new Scanner(pathToFile)) {
                    while (scanner.hasNext()) {
                        String data = scanner.nextLine();
                        String temp = encoded(data);
                        out = out+temp;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return out;
        }

        private String encoded(String data) {
            String out = "";
            for (int i = 0; i < data.length(); i++) {
                int ascii = (int)data.charAt(i);
                char ch;
                if((ascii>=65 && ascii<=90) || (ascii>=97 && ascii<=122)){
                    if(ascii<97)
                        ch = (char) (((int) data.charAt(i) + key -65+26) % 26 + 65);
                    else
                        ch = (char) (((int) data.charAt(i) + key -97+26) % 26 + 97);
                    out = out + ch;
                }else{
                    out = out + data.charAt(i);
                }
            }
            return out;
        }

    }
    static class ImplementUnicodeAlgo{
        int key;
        String data;
        File pathToFile;
        File destination;
        ImplementUnicodeAlgo(int key,String data,File pathToFile,File destination){
            this.key = key;
            this.data = data;
            this.pathToFile = pathToFile;
            this.destination = destination;
            String decoded = decode();
            if(destination!=null) {
                try {
                    FileWriter writer = new FileWriter(destination);
                    for(int i =0;i<decoded.length();i++)
                    {
                        writer.write(decoded.charAt(i));
                    }
                    writer.close();
                }catch ( IOException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println(decoded);
            }
        }

        public String decode(){
            String out = "";
            if(!data.equals("")){
                out = encoded(data);

            }else{
                try (Scanner scanner = new Scanner(pathToFile)) {
                    while (scanner.hasNext()) {
                        String data = scanner.nextLine();
                        String temp = encoded(data);
                        out +=temp;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return out;
        }

        private String encoded(String data) {
            String out="";
            for (int i = 0; i < data.length(); i++) {
                int ascii = data.charAt(i) + key;
                out = out + (char)ascii;
            }
            return out;
        }

    }

    public static class Encryption extends Decode{
        protected Encryption(int key, String data, File pathToFile, File destination, String algo) {
            super(key, data, pathToFile, destination,algo);
        }

    }
    public static class Decryption extends Decode{
        protected Decryption(int key, String data, File pathToFile, File destination, String algo) {
            super(key, data, pathToFile, destination,algo);
        }
    }
    public static void main(String[] args) {
        String mode = "enc";
        int key = 0;
        File pathToFile = null,destination=null;
        String data = "";
        String algo = null;
        for (int i = 0;i<args.length;) {
            if (args[i].equals("-mode")){
                mode = args[i+1];
                i = i+2;
            }
            else if (args[i].equals("-key")) {
                key = Integer.parseInt(args[i+1]);
                i = i+2;
            }
            else if(args[i].equals("-in")){
                pathToFile = new File(args[i+1]);
                i+=2;
            }
            else if(args[i].equals("-out")){
                destination = new File(args[i+1]);
                i+=2;
            }
            else if (args[i].equals("-data")) {
                data = args[i+1];
                i += 2;
            } else if(args[i].equals("-alg")){
                algo = args[i+1];
                i+=2;
            }else{
                i = i+1;
            }
        }
        if (mode.equals("dec")) {
            new Decryption(-key, data, pathToFile, destination,algo);
        }else{
            new Encryption(key, data, pathToFile, destination,algo);
        }
    }
}
