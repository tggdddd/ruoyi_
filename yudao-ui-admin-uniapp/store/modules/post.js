import {
	listSimplePostsApi
} from '@/api/work/post.js'

const post = {
	state: {
		post: {}
	},
	mutations: {
		INIT_POST: (state,data) => {
			state.post = data
		}
	},

	actions: {
		// 获得字典数据
		InitPost({
			commit
		}) {
			return new Promise((resolve, reject) => {
				listSimplePostsApi().then(res => {
					res = res.data
					commit('INIT_POST', res)
					resolve()
				}).catch(error => {
					reject(error)
				})
			})
		},
	},
}
export default post