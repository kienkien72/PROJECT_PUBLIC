package vn.ndkien.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.ndkien.laptopshop.domain.Product;
import vn.ndkien.laptopshop.domain.User;
import vn.ndkien.laptopshop.service.ProductService;
import vn.ndkien.laptopshop.service.UploadService;

@Controller
public class ProductController {
    private final ProductService productService;

    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product/create") // Get
    public String getCreateUserPage(Model model) {
        model.addAttribute("newProduct", new Product());
        // Lấy từ file create.jsp trong user folder admin
        return "admin/product/create";
    }

    // Lưu sản phẩm
    @PostMapping("/admin/product/create")
    public String createProductPage(Model model,
            @ModelAttribute("newProduct") @Valid Product product,
            BindingResult newProductBindingResult,
            @RequestParam("productFile") MultipartFile file) {
        List<FieldError> errors = newProductBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/create";
        }

        String avatar = this.uploadService.handleSaveUploadFile(file, "product");
        product.setImage(avatar);

        this.productService.handleSaveProduct(product);
        return "redirect:/admin/product";
    }

    // Lấy tất cả sản phẩm
    @GetMapping("/admin/product")
    public String getProductPage(Model model) {
        List<Product> products = this.productService.getAllProduct();
        model.addAttribute("product1", products);
        return "admin/product/table-product";
    }

    // Xem Thông tin chi tiết một sản phẩm
    @GetMapping("/admin/product/{id}")
    public String getProductId(Model model, @PathVariable long id) {
        Optional<Product> product = this.productService.fetchProductById(id);
        model.addAttribute("infoPr", product.get());
        model.addAttribute("id", id);
        return "admin/product/show";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id) {
        Optional<Product> currentProduct = this.productService.fetchProductById(id);
        model.addAttribute("newProduct", currentProduct.get());
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String handleUpdateProduct(@ModelAttribute("newProduct") @Valid Product pr,
            BindingResult newProductBindingResult,
            @RequestParam("fileImage") MultipartFile file) {

        // validate
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/update";
        }

        Product currentProduct = this.productService.fetchProductById(pr.getId()).get();
        if (currentProduct != null) {
            // update new image
            if (!file.isEmpty()) {
                String img = this.uploadService.handleSaveUploadFile(file, "product");
                currentProduct.setImage(img);
            }

            currentProduct.setName(pr.getName());
            currentProduct.setPrice(pr.getPrice());
            currentProduct.setQuantity(pr.getQuantity());
            currentProduct.setDetailDesc(pr.getDetailDesc());
            currentProduct.setShortDesc(pr.getShortDesc());
            currentProduct.setFactory(pr.getFactory());
            currentProduct.setTarget(pr.getTarget());

            this.productService.handleSaveProduct(currentProduct);
        }

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProductId(Model model, @PathVariable long id) {
        model.addAttribute("product", new Product());
        model.addAttribute("id", id);

        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String deleteProduct(Model model, @ModelAttribute("newProduct") Product product) {
        this.productService.deleteId(product.getId());
        return "redirect:/admin/product";
    }
}
