import {
	listSimpleUsers
} from '@/api/work/user.js'

const nickName = {
	state: {
		nickName: {}
	},
	mutations: {
		INIT_NICK_NAME: (state,data) => {
			state.nickName = data
		}
	},

	actions: {
		// 获得字典数据
		InitUserNickName({
			commit
		}) {
			return new Promise((resolve, reject) => {
				listSimpleUsers().then(res => {
					res = res.data
					commit('INIT_NICK_NAME', res)
					resolve()
				}).catch(error => {
					reject(error)
				})
			})
		},
	},
}
export default nickName