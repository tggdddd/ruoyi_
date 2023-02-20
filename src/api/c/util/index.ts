import { ontractVO } from '@/api/c/ontract/index'
import request from '@/config/axios'

//获得合同页面
export const getAttachApi = async (params: ontractVO) => {
  return await request.get({ url: '/c/util/getAttach', params })
}
