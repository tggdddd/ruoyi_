<template>
  <view v-if="params.url">
    <web-view :webview-styles="webviewStyles" ref="webview" :src="`${params.url}`"></web-view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        params: {},
        webviewStyles: {
          progress: {
            color: "#FF3333"
          }
        }
      }
    },
    props: {
      src: {
        type: [String],
        default: null
      }
    },
    onLoad(event) {
      this.params = event
	  let params = Object.getOwnPropertyNames (event).filter(e=>e!="__ob__"&&e!="title")
	  this.params.url+="?"
	  for(let param of params){
		  	  		 this.params.url += "&"+param+"="+event[param]
					 }
      if (event.title) {
        uni.setNavigationBarTitle({
          title: event.title
        })
      }
	  this.$refs.webView.$el.addEventListener('touchstart', function(e) {
		  console.log("点击 ")
	    // 阻止默认的点击事件
	    e.preventDefault();
	    // 获取WebView的位置和大小
	    var rect = webView.getBoundingClientRect();
	    // 获取点击位置的坐标
	    var x = e.touches[0].clientX - rect.left;
	    var y = e.touches[0].clientY - rect.top;
	    // 获取WebView的缩放比例
	    var scale = plus.screen.scale;
	    // 获取屏幕密度
	    var density = plus.screen.dpiX / 160;
	    // 将坐标转换为dp单位
	    var xInDp = parseInt(x / (density * scale));
	    var yInDp = parseInt(y / (density * scale));
	    // 创建点击事件
	    var event = new MouseEvent('click', {
	      'view': window,
	      'bubbles': true,
	      'cancelable': true,
	      'clientX': xInDp,
	      'clientY': yInDp
	    });
	    // 触发点击事件
	    webView.dispatchEvent(event);
	  });
    }
	
  }
</script>
