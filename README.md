A modified WebView to bypass android JavaScript Remote Code Execution Bug.<br>
**Tested on Android 2.1+.** (API level 7)<br>
<br>
More info about the bug:<br>
http://labs.bromium.com/2014/07/31/remote-code-execution-on-android-devices/


Usage
=====
Follow the steps bellow.

## 1. Download
Download **SodaWebView.java** and save in your project.

## 2. Change XML
Change XML layout from **WebView** to **br.com.support.SodaWebView**.

### Before
```xml
    <WebView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/webview" />
```

### After
```xml
    <br.com.support.SodaWebView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/webview" />
```

## 3. Return
A function created by Soda returns a JSON object with two properties:<br>
**success** - If method is called with no error, this value is **true** otherwhise is **false**.<br>
**result** - This property contains the result value returned by the Java Object.<br>

```java

var res = droid.sum(10, 10); /* droid was created using addJavaScriptInterface */

if(res.success)
{
	alert('sum: '+res.result);
}

```

## 4. Demos
This repository contains a demo project. The demo project has 7 demos.

### 4.1 Demo 1
This demo writes a message in logcat.
#### Java
```java

public void logMessageError()
{
	Log.e("Message from JS", "Logged message!");
}

```
#### JavaScript
```javascript

droid.logMessageError();

```

### 4.2 Demo 2
This demo writes a custom message in logcat from JS.
#### Java
```java

public void logCustomMessageError(String message)
{
	if(message == null || message.equals("null")) message = "Message is empty! Using default: Hello World!";
	Log.e("Message from JS", message);
}

```
#### JavaScript
```javascript

var message = prompt('Write something and press ENTER. Your message will be visible in logcat.', '');

droid.logCustomMessageError(message);

```

### 4.3 Demo 3
This demo sums two integers.
#### Java
```java

public int sum(int a, int b)
{
	return a+b;
}

```
#### JavaScript
```javascript

var res = droid.sum(11, 11);

document.getElementById('sumResult1').innerHTML = res.result;

```


### 4.4 Demo 4
This demo sums two numbers from user input.
#### Java
```java

public int sumStr(String a, String b)
{
	int iA = 0;
	int iB = 0;
	try
	{
		iA = Integer.parseInt(a);
		iB = Integer.parseInt(b);
	}
	catch(Exception ex)
	{
		Log.e("Sum from JS", "sumStr error.");
	}
	
	return iA+iB;
}

```
#### JavaScript
```javascript

var number1 = prompt('Type number 1', '');
var number2 = prompt('Type number 2', '');

var res = droid.sumStr(number1, number2);

if(res.success)
{
	document.getElementById('sumResult2').innerHTML = res.result;
}
else
{
	alert(res.result);
}

```


### 4.5 Demo 5
Create a string from JS values. 
#### Java
```java

public String stringFormat(int a, String b)
{
	return String.format("Copyright (C) %d %s", a, b);
}

```
#### JavaScript
```javascript

var res = droid.stringFormat(2015, 'Luiz Gustavo Gesswein Cruz');
alert(res.result);

```


### 4.6 Demo 6
Show a Toast.
#### Java
```java

public void showToast(String message)
{
	mActivityInterface.showToast(message);
}

```
#### JavaScript
```javascript

droid.showToast('Hello World!');

```



### 4.7 Demo 7 (Demo 3 with error)
Try to sum two strings in a function that wants two integers.
#### Java
```java

public int sum(int a, int b)
{
	return a+b;
}

```
#### JavaScript
```javascript

var res = droid.sum('1', '2');
if(!res.success) /* success = false */
{
	alert(res.result); /* show error */
}

```

Credits
==========
Luiz Gustavo Gesswein Cruz. ( [@luizgustavogc](https://github.com/luizgustavogc) ).

### License
Released under the Apache License.
