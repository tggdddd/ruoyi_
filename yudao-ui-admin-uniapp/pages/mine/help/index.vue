<template>
  <view class="help-container">
    <view v-for="(item, findex) in list" :key="findex" :title="item.title" class="list-title">
      <view class="text-title">
        <view :class="item.icon"></view>{{ item.title }}
      </view>
      <view class="childList">
        <view v-for="(child, zindex) in item.childList" :key="zindex" class="question" hover-class="hover"
          @click="handleText(child)">
          <view class="text-item">{{ child.title }}</view>
          <view class="line" v-if="zindex !== item.childList.length - 1"></view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        list: [{
            icon: 'iconfont icon-github',
            title: '常见问题',
            childList: [{
              title: '我能否我的修改密码？',
              content: '当然，只需要在修改密码页面修改密码就好。'
            }, {
              title: '忘记密码了怎么办？',
              content: '可以由管理员直接修改密码，当然通过手机号修改也是一个途径。'
            }, {
              title: '为什么别人能看到我的数据？',
              content: '管理员当然能够看到你的数据，必须你的数据由他进行管理'
            }, {
              title: '账号是手机电脑都通用的吗？',
              content: '当然，我想你一定无法忍受记住那么多的账号和密码。'
            }]
          },
          {
            icon: 'iconfont icon-help',
            title: '其他问题',
            childList: [{
              title: '如何退出登录？',
              content: '请点击[我的] - [应用设置] - [退出登录]即可退出登录',
            }, {
              title: '如何修改用户头像？',
              content: '请点击[我的] - [选择头像] - [点击提交]即可更换用户头像',
            }, {
              title: '如何修改登录密码？',
              content: '请点击[我的] - [应用设置] - [修改密码]即可修改登录密码',
            }]
          }
        ]
      }
    },
    methods: {
      handleText(item) {
        this.$tab.navigateTo(`/pages/common/textview/index?title=${item.title}&content=${item.content}`)
      }
    }
  }
</script>

<style lang="scss" scoped>
  page {
    background-color: #f8f8f8;
  }

  .help-container {
    margin-bottom: 100rpx;
    padding: 30rpx;
  }

  .list-title {
    margin-bottom: 30rpx;
  }

  .childList {
    background: #ffffff;
    box-shadow: 0px 0px 10rpx rgba(193, 193, 193, 0.2);
    border-radius: 16rpx;
    margin-top: 10rpx;
  }

  .line {
    width: 100%;
    height: 1rpx;
    background-color: #F5F5F5;
  }

  .text-title {
    color: #303133;
    font-size: 32rpx;
    font-weight: bold;
    margin-left: 10rpx;

    .iconfont {
      font-size: 16px;
      margin-right: 10rpx;
    }
  }

  .text-item {
    font-size: 28rpx;
    padding: 24rpx;
  }

  .question {
    color: #606266;
    font-size: 28rpx;
  }
</style>
