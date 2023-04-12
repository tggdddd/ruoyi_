import store from '@/store'
const post = (vue) => {
	vue.directive('post', {
	  bind: function(el, binding) {
		  let label = "无"
		  const id = binding.value;
		if (store.getters && store.getters.post) {
					  if(store.getters.post.length == undefined || store.getters.post.length == 0){
						  	store.dispatch("InitPost").then(()=>{
					  		let result = store.getters.post.filter(item => item.id == id)
					  		if (result.length != 0) {
					  			label = result[0].name
					  		}
							el.innerHTML = label;
					  		return;
					  	})
					  }else{
						  let result = store.getters.post.filter(item => item.id == id)
							if (result.length != 0) {
								label = result[0].name
							}
						  el.innerHTML = label;
						  return;
					  }
		}
		  el.innerHTML = label;
			
	  }
	});
}
export default post