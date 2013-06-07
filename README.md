# yLoggr


`yLoggr` is a simple and lightweight wrapper for `android.util.Log` class.
It enables you to log any kind of data structure hassle free.
I find this especially useful in cases where real time debugging isn't possible or doesn't work well.

This library has the same interface as the original `android.util.Log` class which you can find here:
**http://developer.android.com/reference/android/util/Log.html**

## How To Use

Simple logging:

    yLoggr.d("This is a simple log text.");

Output:

Logging arrays:

    String[] stringArray = {"this", "is", "a", "String", "array"};
    yLoggr.d(stringArray);

Output:


Logging complex data structures:

    List<Car> cars = new ArrayList<Car>();
    yLoggr.d(cars);

Output:

Logging exceptions:

Output:
    
    

## Including In Your Project

Download the jar file.

## Developed By


* Arda Yigithan Orhan - <ayorhan@ayorhan.com>

## License


    Copyright 2012 Arda Yigithan Orhan

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
