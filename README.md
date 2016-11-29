# RedisAopAnnotation

基于Spring AOP，自定义annotation实现对象及列表通过JSON序列化与反序列化的树形缓存策略。
当前使用Redis，在支持当前设计的前提下可替换其他缓存方案。

### 设计思想
1. 以领域作为根命名空间，建立树形结构的缓存方案。每一个领域树下又分成对象枝及列表枝。
2. 以根节点起，对象枝或者列表枝下，每一个分支下都是一组闭环的业务对象枝。
3. 对于树的每一个节点：子节点对象依赖父节点对象，父节点对象不依赖子节点对象。
4. 缓存key就是从根节点起到所在节点的完整路径。
5. 在更新缓存时，除移除自己之外，可以指定移除列表枝从指定节点起下的所有内容，亦可移除整个列表枝或者不做列表枝移除操作，按实际情况而定。

*因此缓存需支持匹配查找或前缀查找类功能*

### Annotation示例：

###### @ReadAnnotation
    @ReadAnnotation(domain = DOMAIN, clazz = City.class, params = "0")
    public City getCity(long cityId) {
    	return ...;
    }
    
###### @ExpireAnnotation
    @ExpireAnnotation(domain = DOMAIN, prefix = City.class, clazz = Shop.class, params = { "0.id" }, expireType = ExpireType.PREFIX)
    public Shop updateShop(Shop shop) {
        // update shop
        return shop;
    }
    
1. 父节点为对象或列表是可省略prefix。
2. 参数可以为基本类型或者对象。
3. 更新时可自选过期策略。