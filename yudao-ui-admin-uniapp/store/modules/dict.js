import {
	listSimpleDictDatas
} from '@/api/dict/data.js'

const dict = {
	state: {
		dict: {}
	},
	mutations: {
		INIT_DICT_DATA: (state,data) => {
			state.dict = data
		}
	},

	actions: {
		// 获得字典数据
		Init({
			commit
		}) {
			return new Promise((resolve, reject) => {
				listSimpleDictDatas().then(res => {
					const data = {}
					res = res.data
					for (let index in res) {
						if (data[res[index].dictType] == undefined) {
							data[res[index].dictType] = []
						}
						data[res[index].dictType].push(res[index])
					}
					commit('INIT_DICT_DATA', data)
					resolve()
				}).catch(error => {
					reject(error)
				})
			})
		},
	},
}
export default dict