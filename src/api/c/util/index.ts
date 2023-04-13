import { ontractVO } from '@/api/c/ontract/index'
import request from '@/config/axios'

//获得合同页面
export const getAttachApi = async (data: ontractVO) => {
  return await request.post({ url: '/c/util/getAttach', data })
}
