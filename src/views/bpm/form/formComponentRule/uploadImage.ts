import type { UploadUserFile } from 'element-plus'
const label = ' 上传图片'
const name = 'UploadImgs'
let i = 1
const uniqueId = () => `uni${i++}`

const uploadImage = {
  //拖拽组件的图标
  icon: 'icon-upload',
  //拖拽组件的名称
  label,
  //拖拽组件的 key
  name,
  //拖拽组件的生成规则
  rule() {
    //如果在 props 方法中需要修改 rule 的属性,需要提前在 rule 上定义对应的属性
    return {
      //生成组件的名称
      type: name,
      //field 自定不能重复,所以这里每次都会生成一个新的
      field: uniqueId(),
      title: label,
      info: '',
      effect: {
        fetch: ''
      },
      //这里设置组件的默认props配置项, 在下面的 props 方法里面设置无效
      props: {
        modelValue: {
          type: Array as PropType<UploadUserFile[]>,
          required: true
        },
        updateUrl: import.meta.env.VITE_UPLOAD_URL,
        drag: true, // 是否支持拖拽上传 ==> 非必传（默认为 true）
        disabled: false, // 是否禁用上传组件 ==> 非必传（默认为 false）
        limit: 5, // 最大图片上传数 ==> 非必传（默认为 5张）
        fileSize: 5, // 图片大小限制 ==> 非必传（默认为 5M）
        fileType: ['image/jpeg', 'image/png', 'image/gif'], // 图片类型限制 ==> 非必传（默认为 ["image/jpeg", "image/png", "image/gif"]）
        height: '150px', // 组件高度 ==> 非必传（默认为 150px）
        width: '150px', // 组件宽度 ==> 非必传（默认为 150px）
        borderRadius: '8px' // 组件边框圆角 ==> 非必传（默认为 8px）
      }
    }
  },
  //拖拽组件配置项(props)的生成规则
  props() {
    return [
      { type: 'input', field: 'updateUrl', title: '上传地址' },
      { type: 'input', field: 'height', title: '组件高度' },
      { type: 'input', field: 'width', title: '组件宽度' },
      { type: 'input', field: 'borderRadius', title: '组件边框圆角' },
      {
        type: 'select',
        field: 'fileType',
        title: '图片类型',
        props: { multiple: true, allowCreate: true, filterable: true }
      },
      { type: 'input', field: 'fileSize', title: '文件大小MB' },
      {
        type: 'inputNumber',
        field: 'limit',
        title: '图片数量限制'
      },
      { type: 'switch', field: 'drag', title: '拖拽上传' }
    ]
  }
}
export { uploadImage }
