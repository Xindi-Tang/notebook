import Mock from 'mockjs' // 引入mockjs
const Random = Mock.Random
let notes = [] // 用于接受生成数据的数组
for (let i = 0; i < 15; i++) { // 可自定义生成的个数
  let template = {
    'id': i+1, // 可以生成基本数据类型
    'name': Random.string(2, 10), // 生成1到100之间自然数
    'abs': Random.string(4, 100), // 生成1到100之间的整数
    'contentHtml': Random.string(4, 100), // 生成0到100之间的浮点数,小数点后尾数为0到5位
    'contentMD': Random.string(4, 100), // 生成随机字符串,可加参数定义规则
    'author': {'id':1}, // 生成2到10个字符之间的字符串
    'category': {'id':Random.integer(1, 4),
                  'name':Random.string(2, 5)}, // 生成一个随机数组
    'createdTime': Random.date(), // 生成一个随机日期,可加参数定义日期格式
    'lastModifiedTime': Random.date()
  }
  notes.push(template)
}

export default notes;
