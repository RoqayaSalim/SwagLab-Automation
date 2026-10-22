package utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
//import jdk.dynalink.beans.StaticClass;

import java.io.File;
import java.io.IOException;


public class DataDriven {
private static final String file_path ="testData/testData.json";
public static JsonNode jsonReader(){

    ObjectMapper mapper = new ObjectMapper();

    try {
        return mapper.readTree(new File(file_path));
    }catch (IOException e){

       throw new RuntimeException("faild to read tesData.json" , e);


    }

}




}
