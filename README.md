# BubbleView

**一个可以让所有的控件进行拖动的工具，使用简单，只需要一行代码就能让你的控件变得可以拖动起来！**

## 引入

在 build.gradle 中配置:

```
repositories {
    jcenter() // If not already there
}

dependencies {
    compile 'marc.com:bubbleview:1.1'
}
```

## 使用

```Java
BubbleView.attach(findViewById(R.id.bubble_text), new BubbleView.BubbleDisappearListner() {
   @Override
   public void disappear(View view) {
      Toast.makeText(MainActivity.this, view.getId() +" disappeared!", Toast.LENGTH_SHORT).show();
   }
});
```