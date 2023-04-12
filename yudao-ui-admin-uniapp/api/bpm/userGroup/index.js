import request from '@/utils/request'



// 创建用户组
export const createUserGroupApi = async (data) => {
  return await request({
    url: '/bpm/user-group/create',
    data: data,
	method:'post' 
  })
}

// 更新用户组
export const updateUserGroupApi = async (data) => {
  return await request({
    url: '/bpm/user-group/update',
    data: data,
	method:'put'
  })
}

// 删除用户组
export const deleteUserGroupApi = async (id: number) => {
  return await request({ url: '/bpm/user-group/delete?id=' + id ,method:'delete'})
}

// 获得用户组
export const getUserGroupApi = async (id: number) => {
  return await request({ url: '/bpm/user-group/get?id=' + id })
}

// 获得用户组分页
export const getUserGroupPageApi = async (params) => {
  return await request({ url: '/bpm/user-group/page', params })
}

// 获取用户组精简信息列表
export const listSimpleUserGroupsApi = async () => {
  return await request({ url: '/bpm/user-group/list-all-simple' })
}
