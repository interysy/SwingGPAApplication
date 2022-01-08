package GPACalcV2a;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FileManipulator {


    public static List<Module> readFile(Path path) {
        List<Module> result = new ArrayList<>();
        try {
            if (Files.exists(path) && Files.isReadable(path)) {
                List<String> content = Files.readAllLines(path);
                for (String line : content) {
                    if (line == "") {
                        continue;       // just in case a blank line happens to be laced within file
                    } else {
                        String[] splitLine = line.strip().split(" ");
                        int credits = Integer.parseInt(splitLine[splitLine.length - 1]);
                        String name = IntStream.range(0, splitLine.length - 1).mapToObj(i -> splitLine[i]).collect(Collectors.joining(" "));
                        Module module = new Module(name, null, credits);
                        result.add(module);
                    }
                }
                return result;
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
        }


        return new ArrayList<>();
    }

    public static void writeToFile(Path path, String name, int credits) {
        try {
            if (Files.isReadable(path) && Files.isWritable(path)) {
                File file = new File(String.valueOf(path));
                FileWriter fr = new FileWriter(file, true);
                BufferedWriter br = new BufferedWriter(fr);
                br.write("\n" + name + ' ' + credits);
                br.close();
                fr.close();
            }

        } catch (IOException e2) {
            System.out.println("There has been an IOException");
        }

    }


}
