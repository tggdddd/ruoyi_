import request from '@/utils/request'

export const getActivityList = async (params) => {
  return await request({
    url: '/bpm/activity/list',
    params
  })
}
