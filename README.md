# BubbleView

**一个可以让所有的控件进行拖动的工具，使用简单，只需要一行代码就能让你的控件变得可以拖动起来！**

![BuubleView](https://github.com/broderickwang/BubbleViewDemo/blob/master/screenshot/2017-09-08%2010.49.36.gif) 

## 配置：

在 build.gradle 中配置:

```
repositories {
    jcenter() // If not already there
}

dependencies {
    compile 'marc.com:bubbleview:2.0'
}
```

## 使用：

```Java
BubbleView.attach(findViewById(R.id.bubble_text), new BubbleView.BubbleDisappearListner() {
   @Override
   public void disappear(View view) {
      Toast.makeText(MainActivity.this, view.getId() +" disappeared!", Toast.LENGTH_SHORT).show();
   }
});
```


