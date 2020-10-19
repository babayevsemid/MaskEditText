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
    implementation 'com.github.babayevsemid:Gps-tracker:1.0.3' 
}

```

### With EditText 

```
<com.semid.maskedittext.MaskEditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:mask="## ### ## ##"
        app:prefix="+994 "
        app:staticPrefix="true"
        app:hideKeyboard="false"
        android:inputType="phone"
        android:imeOptions="actionNext"
        android:nextFocusDown="@+id/til" />
```

### With TextInputLayout 

```
<com.semid.maskedittext.MaskTextInputLayout
        android:id="@+id/cardTil"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:mask="#### #### ####"
        app:prefix="1234 " 
        app:hideKeyboard="true">

        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="phone"
            android:imeOptions="actionDone" />
    </com.semid.maskedittext.MaskTextInputLayout>
```

### Setup manual
 
```
   val maskUtil = MaskUtil()
   maskUtil.config(prefix, mask, isStaticPrefix)
   maskUtil.hideKeyboardWhenMaskComplete(hideKeyboard)
   
// with TextInputEditText
   maskUtil.setup(yourTextInputEditText)
// with EditText
   maskUtil.setup(yourEditText)
// with TextInputLayout
   maskUtil.setup(yourTextInputLayout)
  
```


### StartWith

maskUtil.startWith(
            arrayListOf(
                "+994 50",
                "+994 51",
                "+994 70"
            )
        )
 
