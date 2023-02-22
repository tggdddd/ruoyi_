import { ontractVO } from '@/api/c/ontract'
import request from '@/config/axios'

//获得合同页面
export const getAttachApi = async (params: ontractVO) => {
  return request.post({
    url: '/c/util/getAttach',
    data: params
  })
}
