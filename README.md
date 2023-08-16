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
     implementation 'com.github.babayevsemid:MaskEditText:2.1.3'
}

```

### With EditText 

```
<com.semid.maskedittext.MaskEditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:mask="## ### ## ##"
        app:hideKeyboard="false"
        android:inputType="phone"
        android:imeOptions="actionNext"
        android:nextFocusDown="@+id/til" />
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
```
 
