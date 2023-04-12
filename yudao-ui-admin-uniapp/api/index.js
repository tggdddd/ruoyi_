import request from '@/utils/request'

export const getChickenSoupApi = async () => {
  return await request({ url: '/system/home/chickenSoupForTheSoul' })
}

export const getIndexInfoApi = async () => {
	return await request({url:'/c/util/appInfo'})
}