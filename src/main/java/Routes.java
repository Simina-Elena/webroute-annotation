public class Routes {

    @WebRoute(path="/animes")
    public String test1(){
        System.out.println("in first route");
        return "this is a page for animes";
    }

    @WebRoute(path = "/mangas")
    public String test2() {
        System.out.println("in second route");
        return "this is a page for mangas";
    }
}
