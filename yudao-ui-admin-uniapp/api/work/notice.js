import request from '@/utils/request'


// 查询公告列表
export const getNoticePage = (params) => {
  return request({ url: '/system/notice/page', params })
}

// 查询公告详情
export const getNotice = (id) => {
  return request.get({ url: '/system/notice/get?id=' + id })
}

// 新增公告
export const createNotice = (data) => {
  return request.post({ url: '/system/notice/create', data })
}

// 修改公告
export const updateNotice = (data) => {
  return request.put({ url: '/system/notice/update', data })
}

// 删除公告
export const deleteNotice = (id) => {
  return request.delete({ url: '/system/notice/delete?id=' + id })
}
