import request from '@/config/axios'

export interface UserInfo {
  userName: string
  dateTime: string
  rank: number
}

// 查询首页的用户信息
export const getUserInfoApi = async () => {
  return await request.get({ url: '/system/home/getInfo' })
}
// 获得心灵鸡汤
export const getChickenSoupApi = async () => {
  return await request.get({ url: '/system/home/chickenSoupForTheSoul' })
}
