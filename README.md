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
     implementation 'com.github.babayevsemid:MaskEditText:1.0.0'
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

### With TextInputEditText

```
 <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/cardTil"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <com.semid.maskedittext.MaskTextInputEditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="phone"
            android:imeOptions="actionDone"
            app:mask="#### #### ####"
            app:prefix="1234 "
            app:staticPrefix="true"
            app:hideKeyboardWhenComplete="true" />
    </com.google.android.material.textfield.TextInputLayout>
```

### Setup manual
 
```
   val maskUtil = MaskUtil()
   maskUtil.config(yourPrefix, yourMask, isStaticPrefix)
   maskUtil.hideKeyboardWhenMaskComplete(hideKeyboard)
   
// with TextInputEditText
   maskUtil.setup(yourTextInputEditText)
// with EditText
   maskUtil.setup(yourEditText)
// with TextInputLayout
   maskUtil.setup(yourTextInputLayout)
  
```
 
### StartWith
  
```
maskUtil.startWith(
            arrayListOf(
                "+994 50",
                "+994 51",
                "+994 70"
            )
        )  
```

 
### Example
  
```
//Card number (1234 5678 9012 3456)
maskUtil.config("", "#### #### #### ####", false)

//Phone number with operators (+994 50 123 45 67) (+994 70 123 45 67) (+994 99 123 45 67) ...
maskUtil.config("+994 ", "## ### ## ##", true) 
maskUtil.startWith(
            arrayListOf(
                "+994 50",
                "+994 51",
                "+994 55",
                "+994 60",
                "+994 70",
                "+994 77",
                "+994 99"
            )
        )  
```
 
