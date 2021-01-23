Confirm = {
  // 回调函数，外部方法，将函数作为参数传递进来，在这里执行
  show: function (message, callback) {
    Swal.fire({
      title: '确认?',
      text: message,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: '立即删除!'
    }).then((result) => {
      if (result.isConfirmed) {
        if (callback) {
          callback();
        }
      }
    })
  }
}