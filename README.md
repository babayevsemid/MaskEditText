# Android MaskEditText

### Installation

Add this to your ```build.gradle``` file

```
repositories {
    maven {
        url "https://jitpack.io"
    }
}

dependencies {
     implementation 'com.github.babayevsemid:MaskEditText:2.1.4'
}

```

### With EditText 

```
<com.semid.maskedittext.MaskEditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:mask="## ### ## ##"
        app:hideKeyboardWhenComplete="false"
        app:maskDividerColor="@color/transparent"
        android:inputType="phone" />
```

### Setup manual
 
```
   yourEditText.setMask(yourMask)
   yourEditText.setHideKeyboardWhenMaskComplete(hideKeyboard)
  
```
 
### Attributes
  
```
app:mask
app:maskDividerColor
app:hideKeyboardWhenComplete

String.mask(yourMask)  // "123456789".mask("(##-(###)-(##)-(##)") //result "(12)-(345)-(67)-(89)"
String.unMask()  // "(12)-(345)-(67)-(89)".unMask() //result "123456789"
```
 
