# AsyncListView
本次Demo实现了使用Android中的异步任务来加载网络上的内容，同时实现了图片的下载与展示
实现的过程中也有一些需要注意的地方，比如网络的访问权限在AndroidManifest中一定要加上，否则不能访问网络。

## 编写Demo时的一些思路与注意事项：
1. 图片和文字要分开来加载，先加载文字，图片在用到的时候才去加载，这样会提高用户体验
2. 由于我们优化ListView的时候使用了复用的布局converttView，所以可能会出现图片错乱的情况，这时可以为对应的imageView设置一个唯一Tag标签，用来匹配传递过来的图片和标识符。
3. 为了节省用户流量等考虑，我们应该将之前加载过的一些内容缓存起来，这里我们用了Map<>来缓存图片，使用一个list集合来缓存加载过的文字信息，以后有更好的方法再去修改。
