// Copyright 2011 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.hughes.util.raf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;


public class SerializableSerializer<T>  implements RAFSerializer<T> {

  @Override
  public void write(RandomAccessFile raf, T t) throws IOException {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    final ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(t);
    oos.close();
    final byte[] bytes = baos.toByteArray();
    raf.writeInt(bytes.length);
    raf.write(bytes);
  }

  @SuppressWarnings("unchecked")
  @Override
  public T read(RandomAccessFile raf) throws IOException {
    final int length = raf.readInt();
    final byte[] bytes = new byte[length];
    raf.read(bytes);
    final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
    final ObjectInputStream ois = new ObjectInputStream(bais);
    Serializable result;
    try {
      result = (Serializable) ois.readObject();
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    ois.close();
    return (T) result;
  }

}
