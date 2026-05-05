package com.example.spring_ai.learning_springai.service;


import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;

import org.springframework.ai.document.Document;
import java.util.List;

@Service
public class NoteReaderService {

    public List<Document> readNotes(Resource resource){
        try{
            TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(resource);
            List<Document> raw = tikaDocumentReader.read();

             if (raw.size()==0){
                 return null;
             }
             return raw;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
//        .out.println("Successfully read " + raw.size() + " document(s) from " + resource.getFilename());        return raw;
    }
}
