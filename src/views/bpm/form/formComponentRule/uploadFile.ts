const label = ' 上传文件'
const name = 'UploadFile'
let i = 1
const uniqueId = () => `UploadFile${i++}`

const uploadFile = {
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
        // 文件列表
        fileList: [],
        // 上传地址
        updateUrl: import.meta.env.VITE_UPLOAD_URL,
        // 上传类型
        fileType: ['doc', 'xls', 'ppt', 'txt', 'pdf'],
        // 文件大小MB
        fileSize: 5,
        // 文件个数
        limit: 5,
        // 自动上传
        autoUpload: true,
        // 拖拽上传
        drag: true,
        // 是否显示提示
        isShowTip: true
      }
    }
  },
  //拖拽组件配置项(props)的生成规则
  props() {
    return [
      { type: 'input', field: 'updateUrl', title: '上传地址' },
      {
        type: 'select',
        field: 'fileType',
        title: '文件类型',
        props: { multiple: true, allowCreate: true, filterable: true }
      },
      { type: 'input', field: 'fileSize', title: '文件大小MB' },
      {
        type: 'inputNumber',
        field: 'limit',
        title: '文件数量限制'
      },
      {
        type: 'switch',
        field: 'autoUpload',
        title: '自动上传',
        props: {
          activeText: '启动',
          inactiveText: '关闭',
          activeValue: true,
          inactiveValue: false
        }
      },
      {
        type: 'switch',
        field: 'drag',
        title: '拖拽上传',
        props: {
          activeText: '启动',
          inactiveText: '关闭',
          activeValue: true,
          inactiveValue: false
        }
      },
      {
        type: 'switch',
        field: 'isShowTip',
        title: '显示提示',
        props: {
          activeText: '启动',
          inactiveText: '关闭',
          activeValue: true,
          inactiveValue: false
        }
      }
    ]
  }
}
export { uploadFile }
