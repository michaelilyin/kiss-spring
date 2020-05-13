package net.kiss.starter.r2dbc.converters

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
class EnumWritingConverter : Converter<Enum<*>, String> {
  override fun convert(source: Enum<*>): String? {
    return source.name
  }
}
