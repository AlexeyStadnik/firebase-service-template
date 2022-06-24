@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@TypeDef(name = "string-array", typeClass = StringArrayType.class)
package com.service.template.user.entity;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.TypeDef;
