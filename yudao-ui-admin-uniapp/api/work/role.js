import request from '@/utils/request'

// 查询角色列表
export const getRolePageApi = (params) => {
  return request({
    url:  '/system/role/page',
    method: 'get',
    params: params
  })
}

// 查询角色（精简)列表
export const listSimpleRolesApi = () => {
  return request({ url: '/system/role/list-all-simple' })
}

// 查询角色详情
export const getRoleApi =  (id) => {
  return request({ url: '/system/role/get?id=' + id })
}

// 新增角色
export const createRoleApi =  (data) => {
  return request({ url: '/system/role/create', data ,method:'post'})
}

// 修改角色
export const updateRoleApi =  (data) => {
  return request({ url: '/system/role/update', data ,method:'put'})
}

// 修改角色状态
export const updateRoleStatusApi =  (data) => {
  return request({ url: '/system/role/update-status', data ,method:'put'})
}

// 删除角色
export const deleteRoleApi =  (id) => {
  return request({ url: '/system/role/delete?id=' + id ,method:'delete'})
}
