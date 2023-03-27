import store from '@/store'
const useName = (vue) => {
	vue.directive('name', {
	  bind: function(el, binding) {
		  let label = "未设定"
		  const id = binding.value;
		if (store.getters && store.getters.nickName) {
					  if(store.getters.nickName.length == undefined || store.getters.nickName.length == 0){
						  	store.dispatch("InitUserNickName").then(()=>{
					  		let result = store.getters.nickName.filter(item => item.id == id)
					  		if (result.length != 0) {
					  			label = result[0].nickname
					  		}
							el.innerHTML = label;
					  		return;
					  	})
					  }else{
						  let result = store.getters.nickName.filter(item => item.id == id)
							if (result.length != 0) {
								label = result[0].nickname
							}
						  el.innerHTML = label;
						  return;
					  }
		}
		  el.innerHTML = label;
			
	  }
	});
}
export default useName