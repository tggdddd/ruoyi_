<template>
	<view>
		<view class="content" v-html="content"></view>
	</view>
</template>

<script>
		import {
			getAttachByIdApi
		} from '@/api/work/contract.js'
	export default {
		data(){
			return{
				content:""
			}
		},
		onLoad: function(option) {
			getAttachByIdApi(option.id).then(res=>{
				this.content = res.data;
			}).catch(error =>{
				this.$model.msgError(error)
				this.$tab.navigateBack();
			});
		}
	}
</script>

<style>
	.content{
		background-color: #fafafa;
	}
</style>