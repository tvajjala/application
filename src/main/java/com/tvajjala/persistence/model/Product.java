package com.tvajjala.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * For time being i have created product with Stock Keeping Unit Entities and Different Catalogs
 *
 * @author ThirupathiReddy V
 */
@Entity
public class Product implements Serializable {

    private static final long serialVersionUID = 4670307037833855133L;

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @JsonIgnore
    private Long id;

    @ApiModelProperty( example = "PROD100200300" )
    @Column( unique = true, nullable = false )
    private String productId;

    @ApiModelProperty( example = "IPHONE7" )
    private String name;

    @ApiModelProperty( example = "IPhoneRed " )
    private String description;

    @ApiModelProperty( example = "750.54" )
    @Column( precision = 19, scale = 4 )
    private Double price;

    @Version
    @Temporal( TemporalType.TIMESTAMP )
    private Date version;

    public Product() {
    }

    public Product( String productId, String name, String description, Double price ) {
        super();
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId( String productId ) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice( Double price ) {
        this.price = price;
    }

    public Date getVersion() {
        return version;
    }

    public void setVersion( Date version ) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( id == null ? 0 : id.hashCode() );
        result = prime * result + ( productId == null ? 0 : productId.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final Product other = (Product) obj;
        if ( id == null ) {
            if ( other.id != null ) {
                return false;
            }
        } else if ( !id.equals( other.id ) ) {
            return false;
        }
        if ( productId == null ) {
            if ( other.productId != null ) {
                return false;
            }
        } else if ( !productId.equals( other.productId ) ) {
            return false;
        }
        return true;
    }

}