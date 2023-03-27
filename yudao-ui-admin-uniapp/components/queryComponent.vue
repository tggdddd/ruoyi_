<template>
	<view>
		<!-- 搜索栏 -->
		<uni-row class="searchBar" v-if="fields!==undefined && fields.length > 0 " >
			<!-- 选择搜索条件 -->
			<uni-col :span="8">
				<uni-data-select class="selectStyle" :clear="false" placeholder="搜索条件" v-model="field" :localdata="fields" @change="change"
					></uni-data-select>
			</uni-col>
			<!-- 输入搜索条件 -->
			<uni-col :span="16">
				<uni-search-bar class="" radius="5" v-model="input" placeholder="搜索条件" clearButton="auto"
					cancelButton="always" @input="searchInput" @confirm="confirm" cancelText="清空" @cancel="cancel" />
			</uni-col>
		</uni-row>
	</view>
</template>

<script>
	import {
		listUser
	} from '@/api/work/user.js'
	export default {
		props:['queryParams','columns'],
		data() {
			return {
				field: "",
				input: "",
				fields: {}
			}
		},
		methods: {
			// 选择框清空
			change(){
				this.input = ""
			},
			// 清空查询条件
			clear() {
				this.$emit('update:queryParams', {
					pageNo: 1,
					pageSize: 20
				})
				this.$emit('confirm');
			},
			// 搜索输入
			searchInput() {
				const params = this.queryParams;
				params[this.field] = this.input
				this.$emit('update:queryParams', params)
			},
			confirm(){
				this.$emit('confirm');
			},
			// 取消所有搜索条件
			cancel(res) {
				this.clear();
				uni.showToast({
					title: '搜索条件已全部清空',
					icon: 'none'
				})
			}
		},
		mounted() {
			if(this.columns.length > 0){
				this.fields = this.columns.filter(e=> e.isSearch==true)
				if(this.fields.length > 0)
					this.field = this.fields[0].value
				}
			}
	}
</script>

<style lang="scss">
	.searchBar {
		.selectStyle {
			padding: 10px;
		}
	}
</style>