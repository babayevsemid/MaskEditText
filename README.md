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
        android:inputType="phone"
        app:mask="## ### ## ##" />
```
 
### Attributes
  
```
app:mask
app:maskDividerColor
app:hideKeyboardWhenComplete

"123456789".mask("(##-(###)-(##)-(##)") <--> "(12)-(345)-(67)-(89)"
"(12)-(345)-(67)-(89)".unMask() <--> "123456789"
```

### Setup manual
 
```
yourEditText.setMask(yourMask)
yourEditText.setHideKeyboardWhenMaskComplete(hideKeyboard)
  
```
 
