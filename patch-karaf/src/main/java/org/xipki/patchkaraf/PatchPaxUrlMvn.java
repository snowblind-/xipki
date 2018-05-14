/*
 *
 * Copyright (c) 2013 - 2018 Lijun Liao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.xipki.patchkaraf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * TODO.
 * @author Lijun Liao
 * @since 3.1.0
 */

public class PatchPaxUrlMvn {

  public PatchPaxUrlMvn() {
  }

  public static void main(String[] args) {
    try {
      System.exit(func(args));
    } catch (IOException ex) {
      System.err.println(ex.getMessage());
      System.exit(-1);
    }
  }

  private static int func(String[] args) throws IOException {
    if (args == null || args.length == 0 || args.length % 2 != 0) {
      return printUsage("");
    }

    String fileName = null;

    for (int i = 0; i < args.length; i += 2) {
      String option = args[i];
      String value = args[i + 1];
      if ("--file".equalsIgnoreCase(option)) {
        fileName = value;
      }
    }

    if (PatchUtil.isBlank(fileName)) {
      return printUsage("file is not specified");
    }

    File file = new File(fileName);
    File tmpNewFile = new File(fileName + ".new");
    BufferedReader reader = new BufferedReader(new FileReader(file));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tmpNewFile));
    try {
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.startsWith("#org.ops4j.pax.url.mvn.localRepository")) {
          writer.write("org.ops4j.pax.url.mvn.localRepository=${karaf.home}/not-exists");
        } else if (line.startsWith("org.ops4j.pax.url.mvn.repositories=")) {
          writer.write(PatchUtil.commentContinuedLine(reader, line));
          writer.write("\norg.ops4j.pax.url.mvn.repositories=http://127.0.0.1/notexists@id=dummy");
        } else {
          writer.write(line);
        }

        writer.write('\n');
      }
    } finally {
      reader.close();
      writer.close();
    }

    File origFile = new File(fileName + ".orig");
    if (!file.renameTo(origFile)) {
      return printUsage("could not rename " + file.getPath() + " to " + origFile.getPath());
    }

    if (!tmpNewFile.renameTo(file)) {
      return printUsage("could not rename " + tmpNewFile.getPath() + " to " + file.getPath());
    }

    System.out.println("Patched file " + fileName);
    return 0;
  }

  private static int printUsage(String message) {
    StringBuilder sb = new StringBuilder();
    if (!PatchUtil.isBlank(message)) {
      sb.append(message).append("\n");
    }
    System.err.println(sb.toString());
    return -1;
  }

}