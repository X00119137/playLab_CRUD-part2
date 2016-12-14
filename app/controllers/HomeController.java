package controllers;

import controllers.*;
import play.api.Environment;
import play.mvc.*;
import play.data.*;
import play.db.ebean.Transactional;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import views.html.*;

// Import models
import models.*;


public class HomeController extends Controller {

       //declare a private formfactory instance

    private FormFactory formFactory;

    //
    @Inject
    public HomeController(FormFactory f) {
        this.formFactory = f;
    }

    public Result index(String name) {
        return ok(index.render("Welcome to the Home page", name));
    }

    public Result about() {
        return ok(about.render());
    }

    public Result products() {

        // Get list of all categories in ascending order
        List<Product> productsList = Product.findAll();
        return ok(products.render(productsList));

    }

     //Render and return the add new product page

    public  Result addProduct () {


        Form<Product> addProductForm = formFactory.form(Product.class);

        return  ok(addProduct.render(addProductForm));
    }

    public Result addProductSubmit() {

        Form<Product> newProductForm = formFactory.form(Product.class).bindFromRequest();

        if(newProductForm.hasErrors()) {
            return badRequest(addProduct.render(newProductForm));
        }


        Product newProduct = newProductForm.get();

        newProduct.save();

        flash("succes", "product" +newProduct.getName()+"has been created");


        return redirect(controllers.routes.HomeController.products());

    }
           public Result deleteProduct(Long id) {

               Product.find.ref(id).delete();

               flash("success","product has been deleted");

               return redirect(routes.HomeController.products());
           }
      

    }






