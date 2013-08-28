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

package com.hughes.util;

import java.util.Map;

public class MapUtil {
  
  public static <K,V> V safeGet(final Map<K,V> map, K key, V defaultValue) {
    if (!map.containsKey(key)) {
      return defaultValue;
    }
    return map.get(key);
  }

  public static <K,V> V safeGetOrPut(final Map<K,V> map, K key, V defaultValue) {
    if (!map.containsKey(key)) {
      map.put(key, defaultValue);
    }
    return map.get(key);
  }

  public static <K,V> V safeGet(final Map<K,V> map, K key, Class<V> valueClass) {
    if (!map.containsKey(key)) {
      try {
        map.put(key, valueClass.newInstance());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return map.get(key);
  }
  
  public static <K,V> V safeRemove(final Map<K,V> map, K key, V defaultValue) {
      if (!map.containsKey(key)) {
        return defaultValue;
      }
      return map.remove(key);
    }


}
