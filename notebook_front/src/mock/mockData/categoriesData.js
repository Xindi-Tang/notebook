import Mock from 'mockjs' // 引入mockjs
const Random = Mock.Random
let categories = [] // 用于接受生成数据的数组
for (let i = 0; i < 4; i++) { // 可自定义生成的个数
  let template = {
    'id': i+1, // 可以生成基本数据类型
    'name': Random.string(2, 5)
  }
  categories.push(template)
}

export default categories;
