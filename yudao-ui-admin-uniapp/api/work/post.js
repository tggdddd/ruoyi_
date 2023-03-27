import request from '@/utils/request'

// 查询岗位列表
export const getPostPageApi = async (params) => {
  return await request({ url: '/system/post/page', params,method:'get' })
}

// 获取岗位精简信息列表
export const listSimplePostsApi = async () => {
  return await request.get({ url: '/system/post/list-all-simple' })
}

// 查询岗位详情
export const getPostApi = async (id) => {
  return await request.get({ url: '/system/post/get?id=' + id })
}

// 新增岗位
export const createPostApi = async (data) => {
  return await request.post({ url: '/system/post/create', data })
}

// 修改岗位
export const updatePostApi = async (data) => {
  return await request.put({ url: '/system/post/update', data })
}

// 删除岗位
export const deletePostApi = async (id) => {
  return await request.delete({ url: '/system/post/delete?id=' + id })
}

// 导出岗位
export const exportPostApi = async (params) => {
  return await request.download({ url: '/system/post/export', params })
}
