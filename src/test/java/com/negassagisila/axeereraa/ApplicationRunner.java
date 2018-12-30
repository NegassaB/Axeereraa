package com.negassagisila.axeereraa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

class ApplicationRunner {

    String run(String fileLocation) {
        FileInputStream dummyFileReader;
        try {
            dummyFileReader = new FileInputStream(new File(fileLocation));
            AxeereraaRunner app = new AxeereraaRunner(dummyFileReader);
            app.run();
            return app.getTextAreaText();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
