const getters = {
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  roles: state => state.user.roles,
  permissions: state => state.user.permissions,
  dict: state => state.dict.dict,
  nickName: state => state.nickName.nickName,
  dept: state => state.dept.dept,
  post: state => state.post.post
}
export default getters
