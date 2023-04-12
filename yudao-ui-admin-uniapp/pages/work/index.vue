<template>
  <view class="work-container">
	  <uni-section title="任务管理" type="line"></uni-section>
	  <view class="grid-body">
	    <uni-grid :column="4" :showBorder="false">
	      <uni-grid-item>
	        <view class="grid-item-box"  @click="clickModule('/pages/work/bpm/processInstance/processInstance')">
	          <uni-icons type="compose" size="30"></uni-icons>
	          <text class="text">我的流程</text>
	        </view>
	      </uni-grid-item>
	  		  <uni-grid-item>
	  		    <view class="grid-item-box"  @click="clickModule('/pages/work/bpm/task/todo')">
	  		      <uni-icons type="calendar" size="30"></uni-icons>
	  		      <text class="text">待办任务</text>
	  		    </view>
	  		  </uni-grid-item>
	  		  <uni-grid-item>
	  		    <view class="grid-item-box"  @click="clickModule('/pages/work/bpm/task/done')">
	  		      <uni-icons type="calendar-filled" size="30"></uni-icons>
	  		      <text class="text">已办任务</text>
	  		    </view>
	  		  </uni-grid-item>
	    </uni-grid>
	  		</view>
	  <uni-section title="合同业绩" type="line"></uni-section>
	  <view class="grid-body">
	    <uni-grid :column="4" :showBorder="false">
	      <uni-grid-item>
	        <view class="grid-item-box"  @click="clickModule('/pages/work/contract/index')">
	          <uni-icons type="wallet-filled" size="30"></uni-icons>
	          <text class="text">合同管理</text>
	        </view>
	      </uni-grid-item>
		  <uni-grid-item>
		    <view class="grid-item-box"  @click="clickModule('/pages/work/perform/index')">
		      <uni-icons type="notification" size="30"></uni-icons>
		      <text class="text">业绩管理</text>
		    </view>
		  </uni-grid-item>
		  <uni-grid-item>
		    <view class="grid-item-box"  @click="clickModule('/pages/work/report/index')">
		      <uni-icons type="calendar-filled" size="30"></uni-icons>
		      <text class="text">报告管理</text>
		    </view>
		  </uni-grid-item>
	    </uni-grid>
		</view>
    <!-- 宫格组件 -->
    <uni-section title="系统管理" type="line"></uni-section>
    <view class="grid-body">
      <uni-grid :column="4" :showBorder="false">
        <uni-grid-item>
          <view class="grid-item-box"  @click="clickModule('/pages/work/user/index')">
            <uni-icons type="person-filled" size="30"></uni-icons>
            <text class="text">用户管理</text>
          </view>
        </uni-grid-item>
        <uni-grid-item>
          <view class="grid-item-box"  @click="clickModule('/pages/work/role/index')">
            <uni-icons type="staff-filled" size="30"></uni-icons>
            <text class="text">角色管理</text>
          </view>
        </uni-grid-item>
        <uni-grid-item>
          <view class="grid-item-box"  @click="clickModule('/pages/work/menu/index')">
            <uni-icons type="color" size="30"></uni-icons>
            <text class="text">菜单管理</text>
          </view>
        </uni-grid-item>
        <uni-grid-item>
          <view class="grid-item-box"  @click="clickModule('/pages/work/dept/index')">
            <uni-icons type="settings-filled" size="30"></uni-icons>
            <text class="text">部门管理</text>
          </view>
        </uni-grid-item>
        <uni-grid-item>
          <view class="grid-item-box"  @click="clickModule('/pages/work/post/index')">
            <uni-icons type="heart-filled" size="30"></uni-icons>
            <text class="text">岗位管理</text>
          </view>
        </uni-grid-item>
        <uni-grid-item>
          <view class="grid-item-box"  @click="clickModule('/pages/work/dict/index')">
            <uni-icons type="bars" size="30"></uni-icons>
            <text class="text">字典管理</text>
          </view>
        </uni-grid-item>
        <uni-grid-item>
          <view class="grid-item-box"  @click="clickModule('/pages/work/setting/index')">
            <uni-icons type="gear-filled" size="30"></uni-icons>
            <text class="text">参数设置</text>
          </view>
        </uni-grid-item>
        <uni-grid-item>
          <view class="grid-item-box"  @click="clickModule('/pages/work/notice/index')">
            <uni-icons type="chat-filled" size="30"></uni-icons>
            <text class="text">通知公告</text>
          </view>
        </uni-grid-item>
        <uni-grid-item>
          <view class="grid-item-box"  @click="clickModule('/pages/work/log/index')">
            <uni-icons type="wallet-filled" size="30"></uni-icons>
            <text class="text">日志管理</text>
          </view>
        </uni-grid-item>
      </uni-grid>
    </view>
  </view>
</template>

<script>
	
  export default {
    data() {
      return {
        current: 0,
        swiperDotIndex: 0,
        data: [{
            image: '/static/images/banner/banner01.jpg'
          },
          {
            image: '/static/images/banner/banner02.jpg'
          },
          {
            image: '/static/images/banner/banner03.jpg'
          }
        ]
      }
    },
    methods: {
      clickBannerItem(item) {
        console.info(item)
      },
      changeSwiper(e) {
        this.current = e.detail.current
      },
      changeGrid(e) {
        this.$modal.showToast('模块建设中~')
      },
	  clickModule(path){
	  		  this.$tab.navigateTo(path)
	  }
    }
  }
</script>

<style lang="scss">
  /* #ifndef APP-NVUE */
  page {
    display: flex;
    flex-direction: column;
    box-sizing: border-box;
    background-color: #fff;
    min-height: 100%;
    height: auto;
  }

  view {
    font-size: 14px;
    line-height: inherit;
  }

  /* #endif */

  .text {
    text-align: center;
    font-size: 26rpx;
    margin-top: 10rpx;
  }

  .grid-item-box {
    flex: 1;
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 15px 0;
  }

  .uni-margin-wrap {
    width: 690rpx;
    width: 100%;
    ;
  }

  .swiper {
    height: 300rpx;
  }

  .swiper-box {
    height: 150px;
  }

  .swiper-item {
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
    flex-direction: column;
    justify-content: center;
    align-items: center;
    color: #fff;
    height: 300rpx;
    line-height: 300rpx;
  }

  @media screen and (min-width: 500px) {
    .uni-swiper-dot-box {
      width: 400px;
      /* #ifndef APP-NVUE */
      margin: 0 auto;
      /* #endif */
      margin-top: 8px;
    }

    .image {
      width: 100%;
    }
  }
</style>
